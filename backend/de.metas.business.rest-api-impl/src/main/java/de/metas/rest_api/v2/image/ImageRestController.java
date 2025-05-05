package de.metas.rest_api.v2.image;

import de.metas.image.AdImage;
import de.metas.image.AdImageId;
import de.metas.image.AdImageRepository;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(ImageRestController.ENDPOINT)
public class ImageRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/images";

	private final AdImageRepository adImageRepository;

	public ImageRestController(
			@NonNull final AdImageRepository adImageRepository)
	{
		this.adImageRepository = adImageRepository;
	}

	@GetMapping("/byId/{imageId}")
	@ResponseBody
	public ResponseEntity<byte[]> getImageById(
			@PathVariable("imageId") final int imageIdInt,
			@RequestParam(name = "maxWidth", required = false, defaultValue = "-1") final int maxWidth,
			@RequestParam(name = "maxHeight", required = false, defaultValue = "-1") final int maxHeight,
			@NonNull final WebRequest request)
	{
		final AdImageId adImageId = AdImageId.ofRepoId(imageIdInt);

		final AdImage adImage = adImageRepository.getById(adImageId);
		final String etag = computeETag(adImage.getLastModified(), maxWidth, maxHeight);
		if (request.checkNotModified(etag))
		{
			// Response: 304 Not Modified
			return newResponse(HttpStatus.NOT_MODIFIED, etag).build();
		}

		return newResponse(HttpStatus.OK, etag)
				.contentType(MediaType.parseMediaType(adImage.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + adImage.getFilename() + "\"")
				.body(adImage.getScaledImageData(maxWidth, maxHeight));
	}

	private static String computeETag(@NonNull final Instant lastModified, int maxWidth, int maxHeight)
	{
		return lastModified + "_" + Math.max(maxWidth, 0) + "_" + Math.max(maxHeight, 0);
	}

	private ResponseEntity.BodyBuilder newResponse(final HttpStatus status, final String etag)
	{
		return ResponseEntity.status(status)
				.eTag(etag)
				.cacheControl(CacheControl.maxAge(Duration.ofSeconds(10)));
	}
}
