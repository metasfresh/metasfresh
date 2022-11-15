import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { getImageData } from '../api/image';
import '../assets/HazardIcon.scss';

const HazardIcon = ({ imageId, caption = '', maxWidth = 40, maxHeight = 40 }) => {
  const [loading, setLoading] = useState(false);
  const [imageData, setImageData] = useState();

  useEffect(() => {
    if (imageId) {
      setLoading(true);
      getImageData({ imageId, maxWidth, maxHeight })
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

  return <img src={imageData} title={caption} className={'hazard-icon'} />;
};

HazardIcon.propTypes = {
  imageId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  caption: PropTypes.string,
  maxWidth: PropTypes.number,
  maxHeight: PropTypes.number,
};

export default HazardIcon;
