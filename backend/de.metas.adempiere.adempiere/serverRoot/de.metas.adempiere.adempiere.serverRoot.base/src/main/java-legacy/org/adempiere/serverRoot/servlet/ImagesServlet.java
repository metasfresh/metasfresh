package org.adempiere.serverRoot.servlet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.serverRoot.util.WebEnv;
import org.adempiere.serverRoot.util.WebUtil;
import org.adempiere.service.IClientDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Optional;

import de.metas.Profiles;
import de.metas.cache.CCache;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Servlet used to server images directly from our database
 *
 * Currently supported query parameters
 * <ul>
 * <li> {@link #PARAM_Width} - optional, if greater than zero, it will scale the image to fit the given width
 * </ul>
 *
 * @author tsa
 *
 */
@WebServlet("/images/*")
@Profile(Profiles.PROFILE_App)
public class ImagesServlet extends HttpServlet
{

	/**
	 *
	 */
	private static final long serialVersionUID = 6310297538246786722L;

	private static final String PARAM_Width = org.compiere.util.WebEnv.IMAGE_PARAM_Width;
	private static final String LOGO = org.compiere.util.WebEnv.LOGO;

	private final int cacheExpireMinutes = 60;
	private final CCache<ArrayKey, Optional<byte[]>> imagesCache = new CCache<>(getClass().getName() + "#ImagesCache", 2, cacheExpireMinutes);
	private final CCache<Integer, Optional<BufferedImage>> logoCache = new CCache<>(I_AD_ClientInfo.Table_Name + "#Logo", 2, cacheExpireMinutes);

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		final int width = WebUtil.getParameterAsInt(request, PARAM_Width);

		final String imageName = extractImageName(request);
		final byte[] pngData = getPNGImageByName(imageName, width);

		if(pngData == null || pngData.length == 0)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No image found for " + imageName);
			return;
		}

		response.setHeader("Content-Type", "image/png");
		response.setHeader("Content-Length", String.valueOf(pngData.length));
		response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");
		response.getOutputStream().write(pngData);
	}

	private String extractImageName(final HttpServletRequest request)
	{
		final String path = request.getPathInfo();
		if (Check.isEmpty(path, true))
		{
			return null;
		}

		final int idx = path.lastIndexOf("/");
		if (idx >= 0)
		{
			return path.substring(idx + 1);
		}
		return path;
	}

	private byte[] getPNGImageByName(final String imageName, final int width)
	{
		// NOTE: it's fine to cache the logo because usually it's small
		final ArrayKey key = Util.mkKey(imageName, width);
		return imagesCache.get(key, new Callable<Optional<byte[]>>()
		{

			@Override
			public Optional<byte[]> call() throws Exception
			{
				final BufferedImage image = retrieveImageByName(imageName);
				if (image == null)
				{
					return Optional.absent();
				}

				final byte[] dataPng = toPngData(image, width);
				return Optional.fromNullable(dataPng);
			}
		}).orNull();
	}

	private final BufferedImage retrieveImageByName(final String imageName)
	{
		if (LOGO.equals(imageName))
		{
			BufferedImage image = retrieveLogoImage();
			if (image == null)
			{
				// Fallback
				image = loadProductLogoImage();
			}
			return image;
		}
		else
		{
			return null;
		}
	}

	private final BufferedImage retrieveLogoImage()
	{
		return logoCache.get(0, new Callable<Optional<BufferedImage>>()
		{

			@Override
			public Optional<BufferedImage> call() throws Exception
			{
				final I_AD_Image logo = retrieveLogoADImage();
				if (logo == null)
				{
					return Optional.absent();
				}

				final byte[] data = logo.getBinaryData();
				if (data == null)
				{
					return Optional.absent();
				}

				final BufferedImage logoImage = ImageIO.read(new ByteArrayInputStream(data));
				return Optional.fromNullable(logoImage);
			}
		}).orNull();
	}

	private final I_AD_Image retrieveLogoADImage()
	{
		final Properties ctx = Env.newTemporaryCtx();
		final I_AD_Client client = retriveAD_Client(ctx);
		if (client == null)
		{
			return null;
		}
		final I_AD_ClientInfo clientInfo = Services.get(IClientDAO.class).retrieveClientInfo(ctx, client.getAD_Client_ID());
		if (clientInfo == null)
		{
			return null;
		}

		I_AD_Image logo = clientInfo.getLogoWeb();
		if (logo == null)
		{
			logo = clientInfo.getLogo();
		}
		return logo;
	}

	private final I_AD_Client retriveAD_Client(final Properties ctx)
	{
		final List<I_AD_Client> clients = Services.get(IClientDAO.class).retrieveAllClients(ctx);
		for (final I_AD_Client client : clients)
		{
			if (client.getAD_Client_ID() > 0)
			{
				return client;
			}
		}

		return null;
	}

	private BufferedImage loadProductLogoImage()
	{
		final Image logo = Adempiere.getProductLogoLarge();
		if (logo == null)
		{
			return null;
		}

		return toBufferedImage(logo);
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param image The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(final Image image)
	{
		if (image instanceof BufferedImage)
		{
			return (BufferedImage)image;
		}

		// Create a buffered image with transparency
		final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		final Graphics2D bGr = bufferedImage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bufferedImage;
	}

	private static byte[] toPngData(final BufferedImage image, final int width)
	{
		if (image == null)
		{
			return null;
		}

		BufferedImage imageScaled;
		final int widthOrig = image.getWidth();
		final int heightOrig = image.getHeight();
		if (width > 0 && widthOrig > 0)
		{
			final double scale = (double)width / (double)widthOrig;
			final int height = (int)(heightOrig * scale);

			imageScaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			final AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			final AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			imageScaled = scaleOp.filter(image, imageScaled);
		}
		else
		{
			imageScaled = image;
		}

		final ByteArrayOutputStream pngBuf = new ByteArrayOutputStream();
		try
		{
			ImageIO.write(imageScaled, "png", pngBuf);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return pngBuf.toByteArray();
	}
}
