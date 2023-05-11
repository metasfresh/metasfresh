# JSON validation
#
# Syntax: validatejson ubl-json-schema-file ubl-json-file

echo
echo "############################################################"
echo Validating $2 with $1
echo "############################################################"
python jsonvalidate.py -q $1 $2 2>&1 >output.txt
if [ $? -eq 0 ]
then echo No schema validation errors.
else cat output.txt; exit 1
fi
