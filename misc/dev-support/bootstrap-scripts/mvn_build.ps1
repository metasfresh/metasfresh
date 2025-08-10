<#
.SYNOPSIS
    PowerShell equivalent of mvn_build.sh - builds metasfresh Maven components sequentially.

.DESCRIPTION
    This script builds the metasfresh project components using Maven in the correct order.
    It sets up the Java environment and builds: parent-pom, de-metas-common, backend,
    camel services, procurement-webui-backend, and federated-rabbitmq.

.NOTES
    File Name      : mvn_build.ps1
    Author         : metas GmbH
    Prerequisite   : Maven and Java must be installed and accessible in PATH
    Copyright 2025 : metas GmbH

.EXAMPLE
    .\mvn_build.ps1
    Runs the complete Maven build process for all components.
#>

# PowerShell equivalent of mvn_build.sh
#
# %L
# master
# %%
# Copyright (C) 2025 metas GmbH
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

# Set error action preference to stop on errors (equivalent to bash 'set -e')
$ErrorActionPreference = "Stop"

# needed if your locally installed "default" java is not the one expected by maven
$env:JAVA_HOME = "c:/Users/tobia/.jdks/temurin-21.0.7"
#$env:JAVA_HOME = "c:/Users/tobia/.jdks/temurin-17.0.15"
#$env:JAVA_HOME = "c:/Users/tobia/.jdks/temurin-1.8.0_452"

$MULTITHREAD_PARAM = ''
#$MULTITHREAD_PARAM = '-T2C'

$ADDITIONAL_PARAMS = '-Djib.skip=true -Dmaven.gitcommitid.skip=true -Dlicense.skip=true -DskipTests'
#$ADDITIONAL_PARAMS = "$ADDITIONAL_PARAMS -DskipTests"
#$ADDITIONAL_PARAMS = "$ADDITIONAL_PARAMS -Dmaven.test.skip=true"

#$GOALS = 'test'
$GOALS = 'clean install'

# Helper function to split parameters, filtering out empty strings
function Split-Params {
    param([string]$ParamString)
    if ([string]::IsNullOrWhiteSpace($ParamString)) {
        return @()
    }
    return $ParamString.Split(' ', [System.StringSplitOptions]::RemoveEmptyEntries)
}

try {
    Write-Host "Starting Maven build process..."
    
    Write-Host "Building parent-pom..."
    $additionalArgs = Split-Params $ADDITIONAL_PARAMS
    $goalArgs = Split-Params $GOALS
    mvn --file ../../parent-pom/pom.xml --settings ../maven/settings.xml @additionalArgs @goalArgs
    if ($LASTEXITCODE -ne 0) { throw "parent-pom build failed" }

    Write-Host "Building de-metas-common..."
    $multithreadArgs = Split-Params $MULTITHREAD_PARAM
    $additionalArgs = Split-Params $ADDITIONAL_PARAMS
    $goalArgs = Split-Params $GOALS
    mvn --file ../../de-metas-common/pom.xml --settings ../maven/settings.xml @multithreadArgs @additionalArgs @goalArgs
    if ($LASTEXITCODE -ne 0) { throw "de-metas-common build failed" }

    Write-Host "Building backend..."
    $multithreadArgs = Split-Params $MULTITHREAD_PARAM
    $additionalArgs = Split-Params $ADDITIONAL_PARAMS
    $goalArgs = Split-Params $GOALS
    mvn --file ../../../backend/pom.xml --settings ../maven/settings.xml @multithreadArgs @additionalArgs @goalArgs
    if ($LASTEXITCODE -ne 0) { throw "backend build failed" }
    
    Write-Host "Building camel services..."
    $multithreadArgs = Split-Params $MULTITHREAD_PARAM
    $additionalArgs = Split-Params $ADDITIONAL_PARAMS
    $goalArgs = Split-Params $GOALS
    mvn --file ../../services/camel/pom.xml --settings ../maven/settings.xml @multithreadArgs @additionalArgs @goalArgs
    if ($LASTEXITCODE -ne 0) { throw "camel services build failed" }
    
    Write-Host "Building procurement-webui-backend..."
    $multithreadArgs = Split-Params $MULTITHREAD_PARAM
    $additionalArgs = Split-Params $ADDITIONAL_PARAMS
    $goalArgs = Split-Params $GOALS
    mvn --file ../../services/procurement-webui/procurement-webui-backend/pom.xml --settings ../maven/settings.xml @multithreadArgs @additionalArgs @goalArgs
    if ($LASTEXITCODE -ne 0) { throw "procurement-webui-backend build failed" }
    
    Write-Host "Building federated-rabbitmq..."
    $multithreadArgs = Split-Params $MULTITHREAD_PARAM
    $additionalArgs = Split-Params $ADDITIONAL_PARAMS
    $goalArgs = Split-Params $GOALS
    mvn --file ../../services/federated-rabbitmq/pom.xml --settings ../maven/settings.xml @multithreadArgs @additionalArgs @goalArgs
    if ($LASTEXITCODE -ne 0) { throw "federated-rabbitmq build failed" }
    
    Write-Host "$(Get-Date): Done"
}
catch {
    Write-Error "Build failed: $_"
    exit 1
}