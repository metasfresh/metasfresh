#!/bin/sh

#
# %L
# metasfresh
# %%
# Copyright (C) 2023 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

# Common utility functions for infrastructure scripts

# Detect if we need winpty (only in interactive TTY on Windows)
# Returns "winpty" if needed, empty string otherwise
get_winpty() {
    if [ -t 0 ]; then
        # stdin is a TTY, use winpty
        echo "winpty"
    else
        # stdin is not a TTY (e.g., piped, redirected, or non-interactive), skip winpty
        echo ""
    fi
}

# Get docker exec flags based on TTY availability
# Returns "-it" if TTY available, "-i" otherwise
get_docker_exec_flags() {
    if [ -t 0 ]; then
        echo "-it"
    else
        echo "-i"
    fi
}

# Auto-detect BRANCH_NAME from git branch
auto_detect_branch_name() {
    CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD 2>/dev/null)
    if [ -z "$CURRENT_BRANCH" ]; then
        echo "!! Unable to detect current git branch !!" >&2
        exit 1
    fi

    # Find the longest matching env file based on prefix
    BEST_MATCH=""
    BEST_MATCH_LENGTH=0

    for ENV_FILE in ../env-files/*.env; do
        # Skip if no .env files exist (glob doesn't expand)
        [ -f "$ENV_FILE" ] || continue
        
        BASENAME=$(basename "$ENV_FILE" .env)
        # Check if current branch starts with this env file name
        case "$CURRENT_BRANCH" in
            "$BASENAME"*)
                LENGTH=${#BASENAME}
                if [ "$LENGTH" -gt "$BEST_MATCH_LENGTH" ]; then
                    BEST_MATCH="$BASENAME"
                    BEST_MATCH_LENGTH="$LENGTH"
                fi
                ;;
        esac
    done

    if [ -z "$BEST_MATCH" ]; then
        echo "!! No matching env file found for branch: $CURRENT_BRANCH !!" >&2
        echo "!! Branch names should start with one of these prefixes: !!" >&2
        for ENV_FILE in ../env-files/*.env; do
            [ -f "$ENV_FILE" ] || continue
            basename "$ENV_FILE" .env
        done
        exit 1
    fi

    echo "$BEST_MATCH"
}

# Resolve BRANCH_NAME from parameter or auto-detect
# Usage: resolve_branch_name "$1"
resolve_branch_name() {
    if [ -n "$1" ]; then
        echo "$1"
    else
        echo "No branch name provided, auto-detecting from current git branch..." >&2
        DETECTED_BRANCH=$(auto_detect_branch_name)
        echo "Using env file: ${DETECTED_BRANCH}.env" >&2
        echo "$DETECTED_BRANCH"
    fi
}
