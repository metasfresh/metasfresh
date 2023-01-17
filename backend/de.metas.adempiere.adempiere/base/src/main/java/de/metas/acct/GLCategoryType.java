package de.metas.acct;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_GL_Category;

@AllArgsConstructor
public enum GLCategoryType implements ReferenceListAwareEnum
{
	Document(X_GL_Category.CATEGORYTYPE_Document),
	Import(X_GL_Category.CATEGORYTYPE_Import),
	Manual(X_GL_Category.CATEGORYTYPE_Manual),
	SystemGenerated(X_GL_Category.CATEGORYTYPE_SystemGenerated),

	;

	private static final ReferenceListAwareEnums.ValuesIndex<GLCategoryType> index = ReferenceListAwareEnums.index(values());

	@Getter @NonNull final String code;

	public static GLCategoryType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
