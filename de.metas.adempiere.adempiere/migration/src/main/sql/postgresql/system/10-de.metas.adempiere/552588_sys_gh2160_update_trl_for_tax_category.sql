update c_taxcategory_trl 
set name = 'Regular Tax Rate 19% (Germany)', istranslated='Y', 
	updatedby=99, /*user-id used to indicate "manual migration"*/
	updated='2019-06-25 08:58:32.935051+03' /*select now();*/
where c_taxcategory_id=1000009
	and ad_language='en_US';