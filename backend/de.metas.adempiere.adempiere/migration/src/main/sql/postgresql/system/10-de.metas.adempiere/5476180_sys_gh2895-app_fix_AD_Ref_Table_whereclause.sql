
UPDATE AD_Ref_Table SET WhereClause=replace(whereclause, 'FROM', 'from'), Updatedby=99, Updated=now() where WhereClause like '%FROM%';
UPDATE AD_Ref_Table SET WhereClause=replace(whereclause, 'SELECT', 'select'), Updatedby=99, Updated=now() where WhereClause like '%SELECT%';
UPDATE AD_Ref_Table SET WhereClause=replace(whereclause, 'WHERE', 'where'), Updatedby=99, Updated=now() where WhereClause like '%WHERE%';
