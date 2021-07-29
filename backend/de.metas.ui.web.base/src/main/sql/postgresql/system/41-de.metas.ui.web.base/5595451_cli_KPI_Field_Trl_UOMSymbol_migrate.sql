update webui_kpi_field_trl trl
set uomsymbol=(select uomsymbol from webui_kpi_field f where f.webui_kpi_field_id=trl.webui_kpi_field_id)
where trl.uomsymbol is null;

