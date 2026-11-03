#!/bin/bash

NODE_VERSION=18
REPORT_FILE_OR_DIR=$1

# Check if REPORT_FILE_OR_DIR is set
if [[ -z "${REPORT_FILE_OR_DIR}" ]]; then
    echo "Error: no report directory or zip file provided as parameter"
    exit 1
fi

# Check if the path is a directory
if [[ -d "${REPORT_FILE_OR_DIR}" ]]; then
    echo "Using '${REPORT_FILE_OR_DIR}' is a directory."
    REPORT_DIR=${REPORT_FILE_OR_DIR}
# Check if the file is a zip file
elif file --mime-type "${REPORT_FILE_OR_DIR}" | grep -q "application/zip"; then
    # Determine the extraction folder name
    REPORT_DIR="${REPORT_FILE_OR_DIR%.zip}"
    
    # Remove existing folder if it exists
    if [[ -d "${REPORT_DIR}" ]]; then
        echo "Removing existing directory: '${REPORT_DIR}'"
        rm -rf "${REPORT_DIR}"
    fi
    
    # Unzip the file
    echo "Unzipping '${REPORT_FILE_OR_DIR}' to '${REPORT_DIR}'"
    mkdir "${REPORT_DIR}"
    unzip -q "${REPORT_FILE_OR_DIR}" -d "${REPORT_DIR}" && echo "Unzip complete. Extracted files are in '${REPORT_DIR}'"
else
    echo "The file '${REPORT_FILE_OR_DIR}' is not an existing zip file nor a directory."
    exit 1;
fi

# Check if ${REPORT_DIR}/html exists
if [[ -d "${REPORT_DIR}/html" ]]; then
    REPORT_DIR="${REPORT_DIR}/html"
    echo "Using ${REPORT_DIR}"
fi



npx "--package=node@$NODE_VERSION" yarn install
npx "--package=node@$NODE_VERSION" yarn playwright show-report "${REPORT_DIR}"

