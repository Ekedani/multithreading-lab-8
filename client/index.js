document.getElementById('multiply-client').addEventListener('click', multiplyClientMatricesOnServer);
document.getElementById('multiply-server').addEventListener('click', multiplyServerMatricesOnServer);
document.getElementById('run-experiment').addEventListener('click', runExperiment);

function convertTextToMatrix(text) {
    const lines = text.split('\n');
    return lines.map(line => line.trim().split(/\s+/));
}

function convertMatrixToText(matrix) {
    const lines = matrix.map(line => line.join(' '));
    return lines.join('\n');
}

async function multiplyClientMatricesOnServer(event) {
    event.preventDefault();
    const a = convertTextToMatrix(document.getElementById('a').value);
    const b = convertTextToMatrix(document.getElementById('b').value);
    try {
        const start = new Date().getTime() / 1000
        const response = await fetch('http://localhost:8080/api/multiply-client', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify({a, b})
        });
        const result = await response.json();
        const end = new Date().getTime() / 1000
        if (result.error) {
            throw new Error(result.error);
        }
        document.getElementById('c').value = convertMatrixToText(result.c);
        document.getElementById('time').innerHTML = `${end - start} s`;
    } catch (e) {
        alert(e.message);
    }
}

async function multiplyClientFilesOnServer(event) {
    event.preventDefault();
    const formData = new FormData();

    formData.append('a', JSON.stringify(document.getElementById('a').value));
    formData.append('b', JSON.stringify(document.getElementById('b').value));

    try {
        const start = new Date().getTime() / 1000
        const response = await fetch('http://localhost:8080/api/multiply-client-files', {
            method: 'POST',
            headers: {
                'Content-Type': 'multipart/form-data',
                'Access-Control-Allow-Origin': '*'
            },
            body: formData
        });
        const result = await response.json();
        const end = new Date().getTime() / 1000
        if (result.error) {
            throw new Error(result.error);
        }
        document.getElementById('c').value = convertMatrixToText(result.c);
        document.getElementById('time').innerHTML = `${end - start} s`;
    } catch (e) {
        alert(e.message);
    }
}

async function multiplyServerMatricesOnServer(event) {
    event.preventDefault();
    const size = document.getElementById('size').value;
    try {
        const start = new Date().getTime() / 1000
        const response = await fetch('http://localhost:8080/api/multiply-server?' + new URLSearchParams({
            size: size,
        }), {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        });
        const result = await response.json();
        const end = new Date().getTime() / 1000
        if (result.error) {
            throw new Error(result.error);
        }
        document.getElementById('c').value = convertMatrixToText(result.c);
        document.getElementById('time').innerHTML = `${end - start} s`;
    } catch (e) {
        alert(e.message);
    }
}

async function runExperiment(event) {
    event.preventDefault();
    const testSizes = [500, 750, 1000, 1250, 1500, 1750, 2000];
    const testResults = [];
    for (let size of testSizes) {
        testResults.push(await runExperimentForSize(size));
    }
    const results = await Promise.all(testResults);
    document.getElementById('experiment-results').innerHTML = results.map(result => {
        return `<tr>
            <td>${result.size}</td>
            <td>${result.client}</td>
            <td>${result.server}</td>
            <td>${result.client / result.server}</td>
        </tr>`
    }).join('')
}

async function runExperimentForSize(size) {
    const testMatrices = {
        a: Array.from({length: size}, () => Array.from({length: size}, () => size)),
        b: Array.from({length: size}, () => Array.from({length: size}, () => size))
    };
    const clientStart = new Date().getTime() / 1000;
    await fetch('http://localhost:8080/api/multiply-client', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(testMatrices)
    });
    const clientEnd = new Date().getTime() / 1000;

    const serverStart = new Date().getTime() / 1000;
    await fetch('http://localhost:8080/api/multiply-server?' + new URLSearchParams({
        size: size
    }), {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        }
    });
    const serverEnd = new Date().getTime() / 1000;
    return {
        size,
        client: clientEnd - clientStart,
        server: serverEnd - serverStart
    }
}

