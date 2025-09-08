export const Printing = 'printing';
export const Comments = 'comments';
export const About = 'about';
export const ChangeCurrentWorkplace = 'changeCurrentWorkplace';

export const hasSaveSupport = (staticModalType) => {
  if (
    staticModalType === Printing ||
    staticModalType === ChangeCurrentWorkplace
  ) {
    return false;
  } else {
    return true;
  }
};
