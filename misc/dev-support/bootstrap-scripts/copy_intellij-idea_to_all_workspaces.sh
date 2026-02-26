#!/bin/bash

#
# Copies the intellij-settings stored int this repo to all workspaces.
# Assumptions:
# - this script is in metasfresh/misc/dev-support/bootstrap-scripts
# - the intellij-settings are in metasfresh/misc/dev-support/intellij-idea/.idea
# - the other workspaces are siblings of this workspace
# - each workspace contains of an .idea folder (the target of this script), the is not included in but a sibling of the checked out repositories' folders
#

# Set the root directory and the folder to be copied
CURRENT_DIR=$(pwd) # Save the current directory position
FOLDER_TO_COPY="$CURRENT_DIR/../intellij-idea/.idea" # Name of the folder to copy
ROOT_DIR="$CURRENT_DIR/../../../../.."

# Check if the provided folder to copy exists in the current directory
if [ ! -d "$FOLDER_TO_COPY" ]; then
    echo "Error: '$FOLDER_TO_COPY' does not exist"
    exit 1
fi

# Navigate to the root directory
cd "$ROOT_DIR" || { echo "Failed to navigate to $ROOT_DIR"; exit 1; }

# Iterate through each subdirectory (no recursion)
for dir in */; do
    if [ -d "$dir" ]; then
        echo "Copying '$FOLDER_TO_COPY' into '$dir'"
#        echo "Press any key to continue..."
#        read -n 1 -s
        cp -r "$FOLDER_TO_COPY" "$dir" || { echo "Failed to copy to $dir"; exit 1; }
    fi
done

# Return to the initial directory
cd "$CURRENT_DIR" || exit

echo ""
echo "Folder '$FOLDER_TO_COPY' copied successfully to all subdirectories of $ROOT_DIR."