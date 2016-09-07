update AD_UI_Column set SeqNo=(case
	when UIStyle='L' then 10
	when UIStyle='R' then 20
	else 30 -- shall not happen
	end)
where SeqNo is null;
