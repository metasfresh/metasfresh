
 DROP INDEX IF EXISTS public.AD_Column_AD_Element_Index;

CREATE INDEX AD_Column_AD_Element_Index
  ON public.ad_column
  USING btree
  (ad_element_id);
    
  
 DROP INDEX IF EXISTS public.ad_field_ad_column;

CREATE INDEX ad_field_ad_column
  ON public.ad_field
  USING btree
  (ad_column_id);
  
  
  
    
  
 DROP INDEX IF EXISTS public.ad_field_ad_Name;
   
 CREATE INDEX ad_field_ad_Name
  ON public.ad_field
  USING btree
  (ad_Name_id);
  
  
  
    
 DROP INDEX IF EXISTS ad_printformatitem_ad_column;
CREATE INDEX ad_printformatitem_ad_column
  ON public.ad_printformatitem
  USING btree
  (ad_column_id);
  
  
  
  
      
  
 DROP INDEX IF EXISTS public.ad_process_para_ad_element;
   
 CREATE INDEX ad_process_para_ad_element
  ON public.ad_process_para
  USING btree
  (ad_element_id);