#!/bin/bash
# Post-process Allure results to add feature IDs as tags
# This copies "feature" labels (F00100, etc.) to "tag" labels
# so they appear in the Tags section of individual test details.
#
# Usage: ./add-feature-tags-to-allure.sh <allure-results-dir>

set -e

RESULTS_DIR="${1:-.}"

if [ ! -d "$RESULTS_DIR" ]; then
  echo "Error: Directory $RESULTS_DIR does not exist"
  exit 1
fi

# Count files to process
FILE_COUNT=$(find "$RESULTS_DIR" -maxdepth 1 -name "*-result.json" -type f 2>/dev/null | wc -l)

if [ "$FILE_COUNT" -eq 0 ]; then
  echo "No result files found in $RESULTS_DIR"
  exit 0
fi

echo "Processing $FILE_COUNT Allure result files in $RESULTS_DIR..."

# Process each result file
MODIFIED=0
for file in "$RESULTS_DIR"/*-result.json; do
  [ -f "$file" ] || continue

  # Use jq to add tag labels for each feature label that matches F followed by digits
  # Only add if not already present as a tag
  UPDATED=$(jq '
    .labels as $existing |
    .labels += [
      .labels[] |
      select(.name == "feature" and (.value | test("^F[0-9]+$"))) |
      .value as $fid |
      if ($existing | map(select(.name == "tag" and .value == $fid)) | length) == 0
      then {name: "tag", value: $fid}
      else empty
      end
    ]
  ' "$file" 2>/dev/null)

  if [ $? -eq 0 ] && [ -n "$UPDATED" ]; then
    echo "$UPDATED" > "$file"
    MODIFIED=$((MODIFIED + 1))
  fi
done

echo "Modified $MODIFIED files to add feature tags"
