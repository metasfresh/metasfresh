#!/bin/bash

# Check if ffmpeg is installed
if ! command -v ffmpeg &> /dev/null; then
  echo "Error: ffmpeg is not installed. Please install it first."
  exit 1
fi

# Check if correct number of parameters are passed
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <filepath> <offset>"
  exit 1
fi

# Get input parameters
FILEPATH="$1"
OFFSET="$2"

# Extract directory, filename, and extension
DIR=$(dirname "$FILEPATH")
FILENAME=$(basename "$FILEPATH")
EXTENSION="${FILENAME##*.}"     # File extension (e.g. mp4)
BASENAME="${FILENAME%.*}"       # Filename without extension

# Define output file path
OUTPUT_FILE="${DIR}/${BASENAME}-slow.${EXTENSION}"

FFMPEG_PARAM=-loglevel debug

# Process the video using ffmpeg
ffmpeg $FFMPEG_PARAM \
  -ss "$OFFSET" \
  -i "$FILEPATH" \
  -vf "setpts=2.0*PTS" \
  -an "$OUTPUT_FILE"

# Check if the operation was successful
if [ $? -ne 0 ]; then
  echo "Error: Failed to process the video."
  exit 1
else
  echo "Video processed successfully. Saved as: $OUTPUT_FILE"
fi
