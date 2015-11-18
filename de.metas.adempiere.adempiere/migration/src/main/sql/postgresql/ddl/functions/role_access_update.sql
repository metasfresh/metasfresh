-- Function: role_access_update()

-- DROP FUNCTION role_access_update();

CREATE OR REPLACE FUNCTION role_access_update()
  RETURNS void AS
$BODY$
DECLARE
   roleaccesslevel      VARCHAR (200);
   roleaccesslevelwin   VARCHAR (200);
   sqlins               VARCHAR (2000);
   r                    RECORD;
BEGIN
   FOR r IN (SELECT   ad_role_id, userlevel, NAME
                 FROM AD_ROLE
                WHERE ismanual = 'N'
             ORDER BY ad_role_id)
   LOOP
      DELETE FROM AD_WINDOW_ACCESS
            WHERE ad_role_id = r.ad_role_id;

      DELETE FROM AD_PROCESS_ACCESS
            WHERE ad_role_id = r.ad_role_id;

      DELETE FROM AD_FORM_ACCESS
            WHERE ad_role_id = r.ad_role_id;

      DELETE FROM AD_WORKFLOW_ACCESS
            WHERE ad_role_id = r.ad_role_id;

      IF r.userlevel = 'S__'                                        -- system
      THEN
         roleaccesslevel := '(''4'',''7'',''6'')';
         roleaccesslevelwin := roleaccesslevel;
      ELSIF r.userlevel = '_C_'                                      -- client
      THEN
         roleaccesslevel := '(''7'',''6'',''3'',''2'')';
         roleaccesslevelwin := roleaccesslevel;
      ELSIF r.userlevel = '_CO'                                -- client + org
      THEN
         roleaccesslevel := '(''7'',''6'',''3'',''2'',''1'')';
         roleaccesslevelwin := roleaccesslevel;
      ELSE                                                    -- org or others
         roleaccesslevel := '(''3'',''1'',''7'')';
         roleaccesslevelwin :=
                        roleaccesslevel || ' AND w.Name NOT LIKE ''%(all)%''';
      END IF;

      sqlins :=
            'INSERT INTO AD_Window_Access (AD_Window_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT DISTINCT w.AD_Window_ID, '
         || r.ad_role_id
         || ',0,0,''Y'', Current_Timestamp,0, Current_Timestamp,0,''Y'' FROM AD_Window w INNER JOIN AD_Tab t ON (w.AD_Window_ID=t.AD_Window_ID) INNER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID) WHERE t.SeqNo=(SELECT MIN(SeqNo) FROM AD_Tab xt WHERE xt.AD_Window_ID=w.AD_Window_ID)AND tt.AccessLevel IN '
         || roleaccesslevelwin;

      EXECUTE sqlins;

      sqlins :=
            'INSERT INTO AD_Process_Access (AD_Process_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT DISTINCT p.AD_Process_ID, '
         || r.ad_role_id
         || ',0,0,''Y'', Current_Timestamp,0, Current_Timestamp,0,''Y'' FROM AD_Process p WHERE AccessLevel IN '
         || roleaccesslevel;

      EXECUTE sqlins;

      sqlins :=
            'INSERT INTO AD_Form_Access (AD_Form_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT f.AD_Form_ID, '
         || r.ad_role_id
         || ',0,0,''Y'', Current_Timestamp,0, Current_Timestamp,0,''Y'' FROM AD_Form f WHERE AccessLevel IN '
         || roleaccesslevel;

      EXECUTE sqlins;

      sqlins :=
            'INSERT INTO AD_WorkFlow_Access (AD_WorkFlow_ID, AD_Role_ID, AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) SELECT w.AD_WorkFlow_ID, '
         || r.ad_role_id
         || ',0,0,''Y'', Current_Timestamp,0, Current_Timestamp,0,''Y'' FROM AD_WorkFlow w WHERE AccessLevel IN '
         || roleaccesslevel;

      EXECUTE sqlins;
   END LOOP;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

