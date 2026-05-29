# Project Name
    Mass Import Order Line Candidates from file

## Description
A Node.js project to read order line candidates data from a file and send it to an API. The Order Line Candidates are read from file `Bestelldaten.mit.Partner.NV.removed.txt`

## Prerequisite
1. Populate the file: `Bestelldaten.mit.Partner.NV.removed.txt` with the desired data to be imported
2. Set the `apiUrl` to the desired endpoint in `importOlCands.js`
3. Set the corresponding `authToken` in `importOlCands.js`

## Installation
To install dependencies, run:

```sh
npm install
```

## Usage
To start the project, use:

```sh
npm start
```