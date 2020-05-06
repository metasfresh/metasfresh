UPDATE AD_Field  SET IncludedTabHeight = 800
where ad_field_ID in (
SELECT field.AD_Field_ID from AD_Field field
JOIN AD_Tab t on field.Included_Tab_ID = t.AD_Tab_ID
JOIN AD_Window w on t.ad_window_ID = w.ad_window_ID
and w.name in ('Lieferung', 'Auftrag', 'Bestellung', 'Rechnung', 'Eingangsrechnung')
)