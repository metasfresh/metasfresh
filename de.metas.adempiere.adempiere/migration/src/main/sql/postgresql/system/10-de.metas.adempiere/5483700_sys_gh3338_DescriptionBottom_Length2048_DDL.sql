
select db_alter_table('C_DocType', 'ALTER TABLE C_DocType ALTER COLUMN DocumentNote  TYPE character varying(2048)');
select db_alter_table('C_Invoice', 'ALTER TABLE C_Invoice ALTER COLUMN descriptionbottom  TYPE character varying(2048)');
select db_alter_table('C_Order', 'ALTER TABLE C_Order ALTER COLUMN descriptionbottom  TYPE character varying(2048)');
select db_alter_table('C_Invoice_Candidate', 'ALTER TABLE C_Invoice_Candidate ALTER COLUMN descriptionbottom  TYPE character varying(2048)');
select db_alter_table('C_OLCand', 'ALTER TABLE C_OLCand ALTER COLUMN descriptionbottom  TYPE character varying(2048)');
select db_alter_table('M_InOut', 'ALTER TABLE M_InOut ALTER COLUMN descriptionbottom  TYPE character varying(2048)');