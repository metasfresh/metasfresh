package de.metas.materialtracking;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import javax.annotation.concurrent.Immutable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.process.SvrProcess;

import de.metas.materialtracking.model.I_M_Material_Tracking;

/**
 * Used as parameter in {@link IMaterialTrackingBL#linkModelToMaterialTracking(MTLinkRequest)} and also passed to the registered {@link IMaterialTrackingListener}s.<br>
 * Use the {@link #builder()} method to get your own instance.
 * 
 * @author ts
 *
 */
@Immutable
public class MTLinkRequest
{
	private final Object model;
	private final IParams params;
	private final I_M_Material_Tracking materialTracking;
	private final boolean assumeNotAlreadyAssigned;
	/** transient because we don't want it in the toString output..e.g. if it is a {@link SvrProcess}, it might not work well with {@link ObjectUtils#toString(Object)}. */
	private final transient ILoggable loggable;

	private MTLinkRequest(final Object model,
			final I_M_Material_Tracking materialTracking,
			final IParams params,
			final boolean assumeNotAlreadyAssigned,
			final ILoggable loggable)
	{
		this.model = model;
		this.materialTracking = materialTracking;
		this.params = params;
		this.assumeNotAlreadyAssigned = assumeNotAlreadyAssigned;
		this.loggable = loggable;
	}

	public static MaterialTrackingRequestBuilder builder()
	{
		return new MaterialTrackingRequestBuilder();
	}

	/**
	 * Return a builder and preset its values from the given <code>template</code>.
	 * 
	 * @param template
	 * @return
	 */
	public static MaterialTrackingRequestBuilder builder(final MTLinkRequest template)
	{
		return new MaterialTrackingRequestBuilder()
				.setAssumeNotAlreadyAssigned(template.isAssumeNotAlreadyAssigned())
				.setLoggable(template.getLoggable())
				.setMaterialTracking(template.getMaterialTracking())
				.setModel(template.getModel())
				.setParams(template.getParams());
	}

	/**
	 *
	 * @return never returns <code>null</code>. If there were no parameters supplied, it return {@link IParams#NULL}.
	 */
	public IParams getParams()
	{
		return params;
	}

	public Object getModel()
	{
		return model;
	}

	/**
	 * Convenience method that uses the {@link InterfaceWrapperHelper} to get the mode as an instance of the given <code>clazz</code>.
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T getModel(final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(model, clazz);
	}

	public I_M_Material_Tracking getMaterialTracking()
	{
		return materialTracking;
	}

	/**
	 * If this is <code>false</code> and the model is already linked, it unassigns the model from old material tracking. If this is <code>true</code> and the model is already linked an exception is
	 * thrown.
	 *
	 * @return
	 */
	public boolean isAssumeNotAlreadyAssigned()
	{
		return assumeNotAlreadyAssigned;
	}

	/**
	 * 
	 * @return never returns <code>null</code>.
	 */
	public ILoggable getLoggable()
	{
		return loggable;
	}

	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public static class MaterialTrackingRequestBuilder
	{
		private Object model;
		private IParams params = IParams.NULL; // default = null
		private I_M_Material_Tracking materialTracking;
		private boolean assumeNotAlreadyAssigned = false; // default = false
		private ILoggable loggable = ILoggable.NULL; // default = null

		private MaterialTrackingRequestBuilder()
		{
			// nothing
		}

		/**
		 * Mandatory.
		 * 
		 * @param model
		 * @return
		 */
		public MaterialTrackingRequestBuilder setModel(final Object model)
		{
			this.model = model;
			return this;
		}

		/**
		 * Mandatory.
		 * 
		 * @param materialTracking
		 * @return
		 */
		public MaterialTrackingRequestBuilder setMaterialTracking(final I_M_Material_Tracking materialTracking)
		{
			this.materialTracking = materialTracking;
			return this;
		}

		/**
		 * Optional. Can be used to pass parameters to particular listeners.
		 * 
		 * @param params
		 * @return
		 */
		public MaterialTrackingRequestBuilder setParams(final IParams params)
		{
			this.params = params;
			return this;
		}

		/**
		 * Optional, the default is <code>false</code>.
		 * 
		 * @param assumeNotAlreadyAssigned
		 * @return
		 */
		public MaterialTrackingRequestBuilder setAssumeNotAlreadyAssigned(final boolean assumeNotAlreadyAssigned)
		{
			this.assumeNotAlreadyAssigned = assumeNotAlreadyAssigned;
			return this;
		}

		/**
		 * Optional. Use this is you want to give the business logic a change to report something.
		 * 
		 * @param loggable
		 * @return
		 */
		public MaterialTrackingRequestBuilder setLoggable(final ILoggable loggable)
		{
			this.loggable = loggable;
			return this;
		}

		public MTLinkRequest build()
		{
			Check.assumeNotNull(model, "model is not null in MaterialTrackingEventBuilder {0}", this);
			Check.assumeNotNull(materialTracking, "materialTracking is not null in MaterialTrackingEventBuilder {0}", this);

			return new MTLinkRequest(model, materialTracking, params, assumeNotAlreadyAssigned, loggable);
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

	}

}
