package de.metas.util;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.Serializable;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public final class MFColor implements Serializable
{
	private static final long serialVersionUID = 155277595960307777L;

	private static final LoadingCache<Color, MFColor> flatColorsCache = CacheBuilder.newBuilder()
			.maximumSize(50)
			.build(new CacheLoader<Color, MFColor>()
			{
				@Override
				public MFColor load(final Color flatColor)
				{
					return newFlatColor(flatColor);
				}
			});

	private final MFColorType type;

	private Color flatColor;

	private final Color textureTaintColor;
	/** Texture Graph URL */
	private final URL textureURL;

	public static final float DEFAULT_TextureCompositeAlpha = 0.7f;
	/** Texture Alpha */
	private final float textureCompositeAlpha;

	private final Color lineBackColor;

	private static final Color DEFAULT_LineColor = new Color(245, 245, 245); // gray-white
	private final Color lineColor;

	private static final float DEFAULT_LineWidth = 1.0f;
	/** Line Width */
	private final float lineWidth;

	private static final int DEFAULT_LineDistance = 5;
	/** Line Distance */
	private final int lineDistance;

	private final Color gradientUpperColor;

	private static final Color DEFAULT_GradientLowerColor = new Color(245, 245, 245); // gray-white
	private final Color gradientLowerColor;

	public static final int DEFAULT_GradientStartPoint = SwingConstants.NORTH_WEST;
	/** Gradient Starting point */
	private final int gradientStartPoint;

	private static final int DEFAULT_GradientRepeatDistance = 100;
	/** Gradient repeat distance in points */
	private final int gradientRepeatDistance;

	public static MFColor defaultOfType(@NonNull final MFColorType type)
	{
		final Color defaultColor = UIManager.getColor("Panel.background");
		switch (type)
		{
			case FLAT:
				return ofFlatColor(defaultColor);
			case GRADIENT:
				return ofGradientColor(defaultColor, DEFAULT_GradientLowerColor, DEFAULT_GradientStartPoint, DEFAULT_GradientRepeatDistance);
			case LINES:
				return ofLinesColor(DEFAULT_LineColor, defaultColor, DEFAULT_LineWidth, DEFAULT_LineDistance);
			case TEXTURE:
				return ofTextureColor(/* textureURL */null, defaultColor, DEFAULT_TextureCompositeAlpha);
			default:
				throw new AdempiereException("Unsupported type: " + type);
		}
	}

	public static MFColor ofFlatColorHexString(final @NonNull String hexString)
	{
		final Color flatColor = Color.decode(hexString);
		return ofFlatColor(flatColor);
	}

	public static MFColor ofFlatColor(final @NonNull Color flatColor)
	{
		try
		{
			return flatColorsCache.get(flatColor);
		}
		catch (final ExecutionException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static final MFColor newFlatColor(final @NonNull Color flatColor)
	{
		return MFColor._builder()
				.type(MFColorType.FLAT)
				.flatColor(flatColor)
				.build();
	}

	/**
	 * Set Background to Gradient colors
	 *
	 * @param upperColor upper Color
	 * @param lowerColor lower Color
	 * @param startPoint Starting point - e.g. SOUTH_WEST see SwingConstants, default NORTH_WEST
	 * @param repeatDistance X/Y Distance to repeat gradient in points - 0 no repeats
	 */
	public static MFColor ofGradientColor(@NonNull final Color upperColor, @NonNull final Color lowerColor, final int startPoint, final int repeatDistance)
	{
		return MFColor._builder()
				.type(MFColorType.GRADIENT)
				.gradientUpperColor(upperColor)
				.gradientLowerColor(lowerColor)
				.gradientStartPoint(startPoint)
				.gradientRepeatDistance(repeatDistance)
				.build();
	}

	/**
	 * Set Background to Texture
	 *
	 * @param textureURL URL to a *.gif or *.jpg graphic file
	 * @param taintColor Color to taint the texture (use white for not tainting it)
	 * @param compositeAlpha Tainting value from 0 (no - FullGraph) to 1 (full - NoGraph)
	 */
	public static MFColor ofTextureColor(@NonNull final URL textureURL, @NonNull final Color taintColor, final float compositeAlpha)
	{
		return MFColor._builder()
				.type(MFColorType.TEXTURE)
				.textureTaintColor(taintColor)
				.textureURL(textureURL)
				.textureCompositeAlpha(compositeAlpha)
				.build();
	}

	/**
	 * Set Background to Lines
	 *
	 * @param lineColor line color
	 * @param backColor background color
	 * @param lineWidth Stroke width in point
	 * @param lineDistance Distance between lines in points
	 */
	public static MFColor ofLinesColor(@NonNull final Color lineColor, @NonNull final Color backColor, final float lineWidth, final int lineDistance)
	{
		return MFColor._builder()
				.type(MFColorType.LINES)
				.lineBackColor(backColor)
				.lineColor(lineColor)
				.lineWidth(lineWidth)
				.lineDistance(lineDistance)
				.build();
	}

	@Builder(toBuilder = true, builderMethodName = "_builder")
	private MFColor(
			@NonNull final MFColorType type,
			//
			final Color flatColor,
			//
			final Color textureTaintColor,
			final URL textureURL,
			final Float textureCompositeAlpha,
			//
			final Color lineBackColor,
			final Color lineColor,
			final Float lineWidth,
			final Integer lineDistance,
			//
			final Color gradientUpperColor,
			final Color gradientLowerColor,
			final Integer gradientStartPoint,
			final Integer gradientRepeatDistance)
	{
		this.type = type;

		if (type == MFColorType.FLAT)
		{
			Check.assumeNotNull(flatColor, "Parameter flatColor is not null");
			this.flatColor = flatColor;
			this.gradientUpperColor = null;
			this.gradientLowerColor = null;
			this.gradientStartPoint = 0;
			this.gradientRepeatDistance = 0;
			this.textureTaintColor = null;
			this.textureURL = null;
			this.textureCompositeAlpha = 0;
			this.lineBackColor = null;
			this.lineColor = null;
			this.lineWidth = 0;
			this.lineDistance = 0;
		}
		else if (type == MFColorType.GRADIENT)
		{
			Check.assumeNotNull(gradientUpperColor, "Parameter gradientUpperColor is not null");
			this.gradientUpperColor = gradientUpperColor;
			this.gradientLowerColor = Util.coalesce(gradientLowerColor, DEFAULT_GradientLowerColor); // lower color
			this.gradientStartPoint = Util.coalesce(gradientStartPoint, DEFAULT_GradientStartPoint);
			this.gradientRepeatDistance = Util.coalesce(gradientRepeatDistance, DEFAULT_GradientRepeatDistance);
			this.flatColor = null;
			this.textureTaintColor = null;
			this.textureURL = null;
			this.textureCompositeAlpha = 0;
			this.lineBackColor = null;
			this.lineColor = null;
			this.lineWidth = 0;
			this.lineDistance = 0;
		}
		else if (type == MFColorType.LINES)
		{
			Check.assumeNotNull(lineBackColor, "Parameter lineBackColor is not null");
			this.lineBackColor = lineBackColor;
			this.lineColor = Util.coalesce(lineColor, DEFAULT_LineColor); // line color
			this.lineWidth = Util.coalesce(lineWidth, DEFAULT_LineWidth);
			this.lineDistance = Util.coalesce(lineDistance, DEFAULT_LineDistance);
			this.flatColor = null;
			this.gradientUpperColor = null;
			this.gradientLowerColor = null;
			this.gradientStartPoint = 0;
			this.gradientRepeatDistance = 0;
			this.textureTaintColor = null;
			this.textureURL = null;
			this.textureCompositeAlpha = 0;
		}
		else if (type == MFColorType.TEXTURE)
		{
			Check.assumeNotNull(textureTaintColor, "Parameter textureTaintColor is not null");
			this.textureTaintColor = textureTaintColor;
			this.textureURL = textureURL;
			this.textureCompositeAlpha = Util.coalesce(textureCompositeAlpha, DEFAULT_TextureCompositeAlpha);
			this.flatColor = null;
			this.gradientUpperColor = null;
			this.gradientLowerColor = null;
			this.gradientStartPoint = 0;
			this.gradientRepeatDistance = 0;
			this.lineBackColor = null;
			this.lineColor = null;
			this.lineWidth = 0;
			this.lineDistance = 0;
		}
		else
		{
			throw new IllegalArgumentException("Unsupported type: " + type);
		}
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[");
		if (isFlat())
		{
			sb.append("Flat").append(" ").append(getColorAsString(getFlatColor()));
		}
		else if (isGradient())
		{
			sb.append("Gradient")
					.append(" Upper=").append(getColorAsString(getGradientUpperColor()))
					.append(",Lower=").append(getColorAsString(getGradientLowerColor()))
					.append(",Start=").append(getGradientStartPoint())
					.append(",RDistance=").append(getGradientRepeatDistance());
		}
		else if (isLine())
		{
			sb.append("Line")
					.append(" Color=").append(getColorAsString(getLineColor()))
					.append(",BackColor=").append(getColorAsString(getLineBackColor()))
					.append(",Width=").append(getLineWidth())
					.append(",Distance=").append(getLineDistance());
		}
		else if (isTexture())
		{
			sb.append("Texture")
					.append(" GraphURL=").append(getTextureURL())
					.append(",Taint=").append(getColorAsString(getTextureTaintColor()))
					.append(",Alpha=").append(getTextureCompositeAlpha());
		}
		sb.append("]");
		return sb.toString();
	}

	private static final String getColorAsString(Color color)
	{
		if (color == null)
		{
			color = SystemColor.control;
		}
		final StringBuilder sb = new StringBuilder("[r=").append(color.getRed())
				.append(",g=").append(color.getGreen())
				.append(",b=").append(color.getBlue())
				.append(",a=").append(color.getAlpha())
				.append("]");
		return sb.toString();
	}

	public boolean isFlat()
	{
		return MFColorType.FLAT.equals(getType());
	}

	public boolean isGradient()
	{
		return MFColorType.GRADIENT.equals(getType());
	}

	public boolean isLine()
	{
		return MFColorType.LINES.equals(getType());
	}

	public boolean isTexture()
	{
		return MFColorType.TEXTURE.equals(getType());
	}

	public MFColor setGradientUpperColor(final Color color)
	{
		if (!isGradient() || color == null)
		{
			return this;
		}
		return toBuilder().gradientUpperColor(color).build();
	}

	public MFColor setGradientLowerColor(final Color color)
	{
		if (!isGradient() || color == null)
		{
			return this;
		}
		return toBuilder().gradientLowerColor(color).build();
	}

	public MFColor setGradientStartPoint(final int startPoint)
	{
		if (!isGradient())
		{
			return this;
		}

		return toBuilder().gradientStartPoint(startPoint).build();
	}

	public MFColor setGradientRepeatDistance(final int repeatDistance)
	{
		if (!isGradient())
		{
			return this;
		}

		return toBuilder().gradientRepeatDistance(repeatDistance).build();
	}

	public MFColor setTextureURL(final URL textureURL)
	{
		if (!isTexture() || textureURL == null)
		{
			return this;
		}

		return toBuilder().textureURL(textureURL).build();
	}

	public MFColor setTextureTaintColor(final Color color)
	{
		if (!isTexture() || color == null)
		{
			return this;
		}
		return toBuilder().textureTaintColor(color).build();
	}

	public MFColor setTextureCompositeAlpha(final float alpha)
	{
		if (!isTexture())
		{
			return this;
		}

		return toBuilder().textureCompositeAlpha(alpha).build();
	}

	public MFColor setLineColor(final Color color)
	{
		if (!isLine() || color == null)
		{
			return this;
		}
		return toBuilder().lineColor(color).build();
	}

	public MFColor setLineBackColor(final Color color)
	{
		if (!isLine() || color == null)
		{
			return this;
		}
		return toBuilder().lineBackColor(color).build();
	}

	public MFColor setLineWidth(final float width)
	{
		if (!isLine())
		{
			return this;
		}

		return toBuilder().lineWidth(width).build();
	}

	public MFColor setLineDistance(final int distance)
	{
		if (!isLine())
		{
			return this;
		}

		return toBuilder().lineDistance(distance).build();
	}

	public MFColor toFlatColor()
	{
		switch (getType())
		{
			case FLAT:
				return this;
			case GRADIENT:
				return ofFlatColor(getGradientUpperColor());
			case LINES:
				return ofFlatColor(getLineBackColor());
			case TEXTURE:
				return ofFlatColor(getTextureTaintColor());
			default:
				throw new IllegalStateException("Type not supported: " + getType());
		}
	}
}
