-- some of the IsMandatory fields have a value of ' ' instead on Y, N or null. This script fixes them.

update AD_Field set IsMandatory=null where IsMandatory=' ';