-- Size S
-- Update the widget widths of specific column Reference_ID
update ad_ui_element
set widgetsize = 'S'
where ad_ui_element_id in (
select uie.ad_ui_element_id
from AD_UI_Element uie
left join ad_field f on uie.ad_field_id = f.ad_field_id
left join ad_column c on f.ad_column_id = c.ad_column_id
left join ad_tab t on uie.ad_tab_id = t.ad_tab_id
left join ad_window w on t.ad_window_id = w.ad_window_id
where true
-- 15 Date
-- 29 Quantity
and c.ad_reference_id in
(15, 29)
);

-- Update the widget widths of specific Column Names
update ad_ui_element
set widgetsize = 'S'
where ad_ui_element_id in (
select uie.ad_ui_element_id
from AD_UI_Element uie
left join ad_field f on uie.ad_field_id = f.ad_field_id
left join ad_column c on f.ad_column_id = c.ad_column_id
left join ad_tab t on uie.ad_tab_id = t.ad_tab_id
left join ad_window w on t.ad_window_id = w.ad_window_id
where true
and c.columnname in (
'C_Order_ID'
,'C_UOM_ID'
,'DocumentNo'
,'DocStatus'
,'C_Invoice_Candidate_HeaderAggregation_ID'
,'C_Invoice_Candidate_HeaderAggregation_Override_ID'
,'C_Invoice_Candidate_HeaderAggregation_Effective_ID'
,'Line'
,'POReference'
'ReferenceNo'
)
);
