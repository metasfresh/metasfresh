update AD_UI_ElementField ef set SeqNo=(select count(1) * 10 from AD_UI_ElementField ef2 where ef2.AD_UI_Element_ID=ef.AD_UI_Element_ID and ef2.AD_UI_ElementField_ID <= ef.AD_UI_ElementField_ID)
where SeqNo is null;
