import { allure } from 'allure-playwright';

/** Allure labels shared by the "Receive HUs" modal specs (feature F65030, epic E0150). */
export const describeReceiveHUsCase = (story) => {
  allure.epic('E0150: Material Receipt');
  allure.tag('F65030: Receive HUs');
  allure.tag('F65030');
  allure.story(story);
  allure.severity('normal');
};
