
SELECT db_alter_Table(
	'AD_Column',
	'ALTER TABLE AD_Column ADD COLUMN IsAutoApplyValidationRule character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT ''N''::bpchar');
