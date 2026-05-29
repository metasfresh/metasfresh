# Project Name
    Mass Import Business Partners one by one from file

## Description
A Node.js project to read partner data from a file and send it to an API. The Business Partners are read from file `Partnerimport.Template.v4.21.10.2024.reduziert.1.txt`

## Prerequisite
1. Populate the file: `Partnerimport.Template.v4.21.10.2024.reduziert.1.txt` with the desired data to be imported
2. Set the `apiUrl` to the desired endpoint in `importPartners_one_by_one.js`
3. Set the corresponding `authToken` in `importPartners_one_by_one.js`

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