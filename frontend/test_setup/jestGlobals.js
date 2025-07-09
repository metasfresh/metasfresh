const { TextEncoder, TextDecoder } = require('util');
const { ReadableStream, WritableStream, TransformStream } = require('stream/web');
const { MessageChannel, MessagePort } = require('worker_threads');

// Add required globals
global.TextEncoder = TextEncoder;
global.TextDecoder = TextDecoder;

global.ReadableStream = ReadableStream;
global.WritableStream = WritableStream;
global.TransformStream = TransformStream;

global.MessagePort = MessagePort;
global.MessageChannel = MessageChannel;
