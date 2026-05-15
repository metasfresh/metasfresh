-- me03#29063: Drop edi_desadv_line_object_v.
--
-- The view's only remaining caller, get_desadv_lines_no_pack_json_fn, has been updated
-- (in script 5802240) to inline the view's logic directly with LATERAL + LIMIT 1 on
-- m_inoutline. Its DDL file at
--   backend/de.metas.edi/src/main/sql/postgresql/ddl/views/desadv_json/edi_desadv_line_object_v.sql
-- is deleted in the same commit.
--
-- get_desadv_packs_json_fn already inlined the same logic for the same reason earlier;
-- this script completes the cleanup by removing the buggy view altogether.

DROP VIEW IF EXISTS "de.metas.edi".edi_desadv_line_object_v;
