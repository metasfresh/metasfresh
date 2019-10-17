alter table AD_Element_Link drop constraint adelement_adelementlink;
alter table AD_Element_Link drop constraint adfield_adelementlink;
alter table AD_Element_Link drop constraint adtab_adelementlink;
alter table AD_Element_Link drop constraint adwindow_adelementlink;

alter table AD_Element_Link add CONSTRAINT adelement_adelementlink FOREIGN KEY (ad_element_id)
    REFERENCES ad_element (ad_element_id) 
    MATCH SIMPLE
    ON UPDATE NO ACTION 
    ON DELETE CASCADE
    DEFERRABLE INITIALLY DEFERRED;

alter table AD_Element_Link add CONSTRAINT adfield_adelementlink FOREIGN KEY (ad_field_id)
    REFERENCES ad_field (ad_field_id) 
    MATCH SIMPLE
    ON UPDATE NO ACTION 
    ON DELETE CASCADE
    DEFERRABLE INITIALLY DEFERRED;

alter table AD_Element_Link add CONSTRAINT adtab_adelementlink FOREIGN KEY (ad_tab_id)
    REFERENCES ad_tab (ad_tab_id) 
    MATCH SIMPLE
    ON UPDATE NO ACTION 
    ON DELETE CASCADE
    DEFERRABLE INITIALLY DEFERRED;

alter table AD_Element_Link add CONSTRAINT adwindow_adelementlink FOREIGN KEY (ad_window_id)
    REFERENCES ad_window (ad_window_id) 
    MATCH SIMPLE
    ON UPDATE NO ACTION 
    ON DELETE CASCADE
    DEFERRABLE INITIALLY DEFERRED;


