package de.metas.ui.web.receiptSchedule;

import de.metas.cache.CCache;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.handlingunits.DefaultHUEditorViewFactory;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.ClientId;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Message_Trl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The receive-HUs view (window "husToReceive") is captioned with a translatable message, in both layout paths
 * (sysconfig AlwaysUseSameLayout = N and Y); every other HU editor keeps the constant "HU Editor".
 */
public class HUsToReceiveViewFactoryTest
{
	private static final String SYSCONFIG_AlwaysUseSameLayout = "de.metas.ui.web.handlingunits.HUEditorViewFactory.AlwaysUseSameLayout";
	private static final String MSG_KEY = "de.metas.ui.web.handlingunits.HUsToReceive.ViewCaption";

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_AD_Message message = newInstanceOutOfTrx(I_AD_Message.class);
		message.setValue(MSG_KEY);
		message.setMsgText("HUs vereinnahmen");
		save(message);
		createTrl(message, "en_US", "Receive HUs");
		createTrl(message, "de_CH", "HUs vereinnahmen");
	}

	private static void createTrl(final I_AD_Message message, final String adLanguage, final String text)
	{
		final I_AD_Message_Trl trl = newInstanceOutOfTrx(I_AD_Message_Trl.class);
		trl.setAD_Message_ID(message.getAD_Message_ID());
		trl.setAD_Language(adLanguage);
		trl.setMsgText(text);
		trl.setIsTranslated(true);
		save(trl);
	}

	private static void setAlwaysUseSameLayout(final boolean value)
	{
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_AlwaysUseSameLayout, value, ClientId.SYSTEM, OrgId.ANY);
	}

	/** Replaces the database-backed SqlViewBinding (needs the application dictionary) by a stub; the caption does not depend on it. */
	private static <T extends HUEditorViewFactoryTemplate> T withStubbedBinding(final T factory) throws Exception
	{
		final SqlViewBinding binding = Mockito.mock(SqlViewBinding.class);
		Mockito.when(binding.getViewFilterDescriptors()).thenReturn(NullDocumentFilterDescriptorsProvider.instance);

		final Field cacheField = HUEditorViewFactoryTemplate.class.getDeclaredField("sqlViewBindingCache");
		cacheField.setAccessible(true);
		@SuppressWarnings("unchecked") final CCache<Integer, SqlViewBinding> cache = (CCache<Integer, SqlViewBinding>)cacheField.get(factory);
		cache.put(0, binding);
		return factory;
	}

	/** The constructor is protected (Spring-instantiated); the real class is still what is tested. */
	private static DefaultHUEditorViewFactory newDefaultHUEditorViewFactory() throws Exception
	{
		final Constructor<DefaultHUEditorViewFactory> constructor = DefaultHUEditorViewFactory.class.getDeclaredConstructor(Optional.class);
		constructor.setAccessible(true);
		return constructor.newInstance(Optional.empty());
	}

	private void assertReceiveCaptions(final boolean alwaysUseSameLayout) throws Exception
	{
		setAlwaysUseSameLayout(alwaysUseSameLayout);
		final HUsToReceiveViewFactory factory = withStubbedBinding(new HUsToReceiveViewFactory());

		final ViewLayout layout = factory.getViewLayout(HUsToReceiveViewFactory.WINDOW_ID, JSONViewDataType.grid, null);

		assertThat(layout.getCaption("de_DE")).isEqualTo("HUs vereinnahmen");
		assertThat(layout.getCaption("de_CH")).isEqualTo("HUs vereinnahmen");
		assertThat(layout.getCaption("en_US")).isEqualTo("Receive HUs");
	}

	@Test
	void receiveViewCaption_translated_whenLayoutCustomized() throws Exception
	{
		assertReceiveCaptions(false);
	}

	@Test
	void receiveViewCaption_translated_whenAlwaysUseSameLayout() throws Exception
	{
		assertReceiveCaptions(true);
	}

	@Test
	void defaultHUEditorCaption_unchanged() throws Exception
	{
		for (final boolean alwaysUseSameLayout : new boolean[] { false, true })
		{
			setAlwaysUseSameLayout(alwaysUseSameLayout);
			final DefaultHUEditorViewFactory factory = withStubbedBinding(newDefaultHUEditorViewFactory());

			final ViewLayout layout = factory.getViewLayout(WindowId.fromJson(WEBUI_HU_Constants.WEBUI_HU_Window_ID_String), JSONViewDataType.grid, null);

			assertThat(layout.getCaption("de_DE")).isEqualTo("HU Editor");
			assertThat(layout.getCaption("en_US")).isEqualTo("HU Editor");
		}
	}
}
