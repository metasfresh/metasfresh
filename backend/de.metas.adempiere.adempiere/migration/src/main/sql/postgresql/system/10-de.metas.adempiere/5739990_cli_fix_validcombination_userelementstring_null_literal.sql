select backup_table('c_validcombination');

update c_validcombination set userelementstring1=null where TRIM(userelementstring1) IN ('null', '');
update c_validcombination set userelementstring2=null where TRIM(userelementstring2) IN ('null', '');
update c_validcombination set userelementstring3=null where TRIM(userelementstring3) IN ('null', '');
update c_validcombination set userelementstring4=null where TRIM(userelementstring4) IN ('null', '');
update c_validcombination set userelementstring5=null where TRIM(userelementstring5) IN ('null', '');
update c_validcombination set userelementstring6=null where TRIM(userelementstring6) IN ('null', '');
update c_validcombination set userelementstring7=null where TRIM(userelementstring7) IN ('null', '');
