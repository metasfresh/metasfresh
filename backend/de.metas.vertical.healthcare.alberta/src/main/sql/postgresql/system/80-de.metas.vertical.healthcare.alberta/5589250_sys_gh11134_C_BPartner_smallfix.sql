-- 2021-05-19T20:30:44.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select max(CASE WHEN albertarole = ''PD'' THEN ''Y'' ELSE ''N'' END)         from c_bpartner_albertarole bp         where bp.c_bpartner_id = c_bpartner.c_bpartner_id)',Updated=TO_TIMESTAMP('2021-05-19 23:30:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573949
;

