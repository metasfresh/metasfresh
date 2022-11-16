import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { getImageData } from '../api/image';

const HazardIcon = ({ imageId, caption = '', size = 40 }) => {
  const [loading, setLoading] = useState(false);
  const [imageData, setImageData] = useState();

  useEffect(() => {
    if (imageId) {
      setLoading(true);
      getImageData({ imageId, maxWidth: size, maxHeight: size })
        .then((data) => {
          setImageData(data);
          setLoading(false);
        })
        .catch((error) => {
          console.warn(`Failed loading image`, { imageId, error });
          setImageData(null);
          setLoading(false);
        });
    } else {
      setImageData(null);
      setLoading(false);
    }
  }, [imageId]);

  if (loading || imageData == null) {
    return null;
  }

  return <img src={imageData} title={caption} width={size} height={size} />;
};

HazardIcon.propTypes = {
  imageId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  caption: PropTypes.string,
  size: PropTypes.number.isRequired,
};

export default HazardIcon;
