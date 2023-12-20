@echo off
rem JSON validation
rem
rem Syntax: validatejson ubl-json-schema-file ubl-json-file

echo.
echo "############################################################"
echo Validating %2 with %1
echo "############################################################"
@echo off
python jsonvalidate.py -q %1 %2 2>output.txt
if %errorlevel% neq 0 goto :error
echo No schema validation errors.
goto :done

:error
type output.txt

:done
