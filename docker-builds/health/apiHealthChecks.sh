#!/bin/bash

#
# %L
<<<<<<< HEAD
# work-javaupdate
=======
# metas
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
# %%
# Copyright (C) 2024 metas GmbH
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

hasErrors=false

echo "*********************************************************"
echo "========================================================="
echo " running health checks ..."
echo "========================================================="

echo ""
echo ""
echo "========================================================="
echo " window health check ..."
echo "---------------------------------------------------------"

healthJson=$(jq . <<< $(curl --silent localhost:8080/rest/api/window/health -H "Accept: application/json"))
countErrors=$(jq '.countErrors' <<< $healthJson)

echo ""
echo "---------------------------------------------------------"
echo " start localhost:8080/rest/api/window/health response"
echo "---------------------------------------------------------"
echo "$healthJson"
echo "---------------------------------------------------------"
echo " end localhost:8080/rest/api/window/health response"
echo "---------------------------------------------------------"

if [ "$countErrors" = "0" ]
then
  echo ""
  echo "OK"
  echo ""
else
  hasErrors=true
  echo ""
  echo "NOT OK"
  echo ""
fi

echo "---------------------------------------------------------"
echo " window health check done"
echo "========================================================="


echo ""
echo ""
echo "========================================================="
echo " process health check ..."
echo "---------------------------------------------------------"

healthJson=$(jq . <<< $(curl --silent localhost:8080/rest/api/process/health -H "Accept: application/json"))
countErrors=$(jq '.countErrors' <<< $healthJson)

echo ""
echo "---------------------------------------------------------"
echo " start localhost:8080/rest/api/process/health response"
echo "---------------------------------------------------------"
echo "$healthJson"
echo "---------------------------------------------------------"
echo " end localhost:8080/rest/api/process/health response"
echo "---------------------------------------------------------"

if [ "$countErrors" = "0" ]
then
  echo ""
  echo "OK"
  echo ""
else
  hasErrors=true
  echo ""
  echo "NOT OK"
  echo ""
fi

echo "---------------------------------------------------------"
echo " process health check done"
echo "========================================================="


echo ""
echo ""
echo "========================================================="
echo " view health check ..."
echo "---------------------------------------------------------"

healthJson=$(jq . <<< $(curl --silent localhost:8080/rest/api/view/health -H "Accept: application/json"))
countErrors=$(jq '.countErrors' <<< $healthJson)

echo ""
echo "---------------------------------------------------------"
echo " start localhost:8080/rest/api/view/health response"
echo "---------------------------------------------------------"
echo "$healthJson"
echo "---------------------------------------------------------"
echo " end localhost:8080/rest/api/view/health response"
echo "---------------------------------------------------------"

if [ "$countErrors" = "0" ]
then
  echo ""
  echo "OK"
  echo ""
else
  hasErrors=true
  echo ""
  echo "NOT OK"
  echo ""
fi

echo "========================================================="
echo " view health check done"
echo "========================================================="


echo ""
echo ""
echo "========================================================="
echo " running health checks done"
echo "========================================================="
echo "*********************************************************"

if [ "$hasErrors" = true ] ; then
  echo ""
  echo ""
  echo "*********************************************************"
  echo "========================================================="
  echo " failure"
  echo "========================================================="
  echo "*********************************************************"
  exit 1
fi

  echo ""
  echo ""
  echo "*********************************************************"
  echo "========================================================="
  echo " success"
  echo "========================================================="
  echo "*********************************************************"
<<<<<<< HEAD
exit 0
=======
exit 0

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
