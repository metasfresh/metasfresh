
# Welcome

Thank you very much for your interest contributing to the **metasfresh webui frontend** which is implemented in [react.js](https://facebook.github.io/react/).

We highly appreciate your effort and want make it as easy as we can.

# License

By opening a pull request for this repository, you license your contribution under the

**GNU GENERAL PUBLIC LICENSE (GPL) Version 2 or any later version**

You can find a copy of the license [here](https://github.com/metasfresh/metasfresh/blob/master/LICENSE.md).

Note that we intent to switch to GPL-3 in the future.

Information about the GPL-3 and about the GPL in general can be found [here](https://www.gnu.org/licenses/quick-guide-gplv3.html).

Additional information about the underlying principles of "copyleft" can be found in [this wikipedia article](https://en.wikipedia.org/wiki/Copyleft).

Note that your contribution remains yours and you can still lincense it as your own work under any other license you wish.
We are allowed to use it for metasfresh under the GPL version two, three, or any possible future version.

# How to contribute

* If you don't yet have one, create a github account for yourself.
* Create a fork of the metasfresh repository you want to contribute to.
  * **Important** please fork from the `master` branch. If you need to fork from any other branch, please consult with us first, in order to avoid possible problems down the road
  * Note that if you are a frequent contributor, we will grant commit rights to our repository, so you can actually branch instead of forking each time.
* Make your contribution and create a pull request.
* We will review and comment on the pull request. We may suggest some changes or improvements or alternatives. We will always thread your work and of course also yourself with respect, even in case we choose not to incorporate it into metasfresh.

The process of creating and maintaning a fork with git and github is explained in [this article](https://help.github.com/articles/fork-a-repo/). Note that at that article's end, there is also a link to [this article](https://help.github.com/articles/using-pull-requests/) about pull requests.

If you need help, don't hesitate to contact us, for example on our [metasfresh-webui-frontend gitter chat](https://gitter.im/metasfresh/metasfresh-webui-frontend).

# Aim for efficient and clean code

* Avoid creating redundant properties
  * negative example: we have both of `disableAutoFocus`, `enableAutoFocus`

* only mutate the state if it's actually needed

* Follow the documentation and best practice. In particular the documentation about:
  * [state-and-lifecycle](https://facebook.github.io/react/docs/state-and-lifecycle.html)
  * [optimizing-performance](https://facebook.github.io/react/docs/optimizing-performance.html)

# How to get help

* We have a gitter chat dedicated to this repository. You can enter it at https://gitter.im/metasfresh/metasfresh-webui-frontend
