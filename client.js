var net = require('net');

var client = new net.Socket();

client.connect(3500, '127.0.0.1', () => {
	console.log('Connected');
});

client.on('data', (data) => {
	process.stdout.write('\x1b[93m' + data + '\x1b[0m');
});

client.on('close', () => {
	console.log('Connection closed');
});

process.stdin.on('data', (data) => {
    client.write(data);
});