#!/bin/bash

DOCKERHUB_USERNAME="$1"
DOCKERHUB_REPO="$2"
DOCKERHUB_TOKEN="${DOCKERHUB_TOKEN}"

echo "DOCKERHUB_REPO=$DOCKERHUB_REPO"
echo "DOCKERHUB_USERNAME=$DOCKERHUB_USERNAME"

# Images that ore older than RETENTION_DATE shall be deleted 
#RETENTION_DATE=$(date -d '7 days ago' --utc +%Y-%m-%dT%H:%M:%SZ)
RETENTION_DATE=$(date -d '1 month ago' --utc +%Y-%m-%dT%H:%M:%SZ)
echo "RETENTION_DATE=$RETENTION_DATE"

# Initial page number
PAGE=1
PAGE_SIZE=100

while : ; do
  
  # Initialize the deletion flag
  DELETED_ANY_TAGS=false
  
  # Fetch a page of tags from DockerHub
  TAGS=$(curl -s -H "Authorization: JWT $DOCKERHUB_TOKEN" "https://hub.docker.com/v2/repositories/${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}/tags/?page_size=${PAGE_SIZE}&page=${PAGE}")
  #echo $TAGS

  ## Check if there are no more tags
  if [[ "$(echo "${TAGS}" | jq '.results | length')" -eq "0" ]]; then
    break
  fi
  
  # Process each tag on the current page
  for ROW in $(echo "${TAGS}" | jq -r '.results[] | @base64'); do
    _jq() {
        echo "${ROW}" | base64 --decode | jq -r "${1}"
    }
      
    TAG_NAME=$(_jq '.name')
    LAST_UPDATED=$(_jq '.last_updated')
    
    if [[ "${LAST_UPDATED}" < "${RETENTION_DATE}" ]]; then
      echo "LAST_UPDATED=${LAST_UPDATED} => delete ${TAG_NAME}"
      
      #echo "Press any key to continue"
      #read -n 1 -s  # waits for a single key press without echoing
      
      DELETE_URL="https://hub.docker.com/v2/repositories/${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}/tags/${TAG_NAME}/"
      DELETE_RESPONSE=$(curl -s -X DELETE -H "Authorization: JWT $DOCKERHUB_TOKEN" "${DELETE_URL}")
      #echo "Response: ${DELETE_RESPONSE}"
      
      DELETED_ANY_TAGS=true
      
    else
      echo "LAST_UPDATED=${LAST_UPDATED} => retain ${TAG_NAME}"
    fi
  done
  
  # If no tags were deleted on this page, get the next one
  if [ "$DELETED_ANY_TAGS" = false ]; then
      # Go to the next page
      echo "Nothing deleted from page $PAGE => fetch next page"
      PAGE=$((PAGE + 1))
  else
    echo "Some tags were deleted; fetch same page again"
  fi
  
done

echo $(date)
echo "Process completed."