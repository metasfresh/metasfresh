import currentDevice from 'current-device';

export function getSizeClass(col) {
  const { widgetType, size } = col;
  const lg = ['List', 'Lookup', 'LongText', 'Date', 'DateTime', 'Time'];
  const md = ['Text', 'Address', 'ProductAttributes'];

  if (size) {
    switch (size) {
      case 'S':
        return 'td-sm';
      case 'M':
        return 'td-md';
      case 'L':
        return 'td-lg';
    }
  } else {
    if (lg.indexOf(widgetType) > -1) {
      return 'td-lg';
    } else if (md.indexOf(widgetType) > -1) {
      return 'td-md';
    } else {
      return 'td-sm';
    }
  }
}

export function handleCopy(e) {
  e.preventDefault();

  const cell = e.target;
  const textValue = cell.value || cell.textContent;

  e.clipboardData.setData('text/plain', textValue);
}

export function handleOpenNewTab(selected, type) {
  for (let i = 0; i < selected.length; i++) {
    window.open(`/window/${type}/${selected[i]}`, '_blank');
  }
}

export function shouldRenderColumn(column) {
  if (
    !column.restrictToMediaTypes ||
    column.restrictToMediaTypes.length === 0
  ) {
    return true;
  }

  const deviceType = currentDevice.type;
  let mediaType = 'tablet';

  if (deviceType === 'mobile') {
    mediaType = 'phone';
  } else if (deviceType === 'desktop') {
    mediaType = 'screen';
  }

  return column.restrictToMediaTypes.indexOf(mediaType) !== -1;
}
