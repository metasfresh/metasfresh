# <img src='/images/metasfresh-logo-slogan-969x248.png' height='60' alt='metasfresh - We do Open Source ERP' aria-label='metasfresh.com' /></a>

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/30ecd0d9ba8a4561a60335644b592418)](https://www.codacy.com/gh/metasfresh/metasfresh?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=metasfresh/metasfresh&amp;utm_campaign=Badge_Grade)
[![release](https://img.shields.io/badge/release-5.173-blue.svg)](https://github.com/metasfresh/metasfresh/releases/tag/5.173)
[![license](https://img.shields.io/badge/license-GPL-blue.svg)](https://github.com/metasfresh/metasfresh/blob/master/LICENSE.md)
<!-- [![Gitter](https://img.shields.io/gitter/room/nwjs/nw.js.svg)](https://gitter.im/metasfresh) -->

![Twitter Follow](https://img.shields.io/twitter/follow/metasfresh?style=social)


metasfresh is a responsive, Free and Open Source ERP System. Our aim is to create fast and easy-to-use enterprise software with an outstanding user experience.

> **__We do Open Source ERP__**

Equipped with wide and detailed functionality, metasfresh fits for companies from industry and trade that are searching business software that provides high scalability and flexibility.

It has a 3-tier architecture with Rest-API and a Web User Frontend developed in HTML5/ ReactJS/ Redux.

![metasfresh-sales-order](https://github.com/metasfresh/metasfresh/blob/master/images/sales-order-recording-metasfresh.gif)
<!-- ![metasfresh-sales-order](https://user-images.githubusercontent.com/13365687/36896187-f5ed2e48-1e11-11e8-9c41-a7878c148f81.gif) -->

<img src="/images/screenshot-kpi-dashboard-new.png" width="33%" alt="KPI Dashboard"></img> <img src="/images/screenshot-sales-order-new.png" width="33%" alt="Sales Order Window"></img> <img src="/images/screenshot-material-receipt-new.png" width="33%" alt="Material Receipt Window"></img>

## Installation
We publish a stable Release of metasfresh every Friday - ok, we skip 1 week at the end of the year ;-) . You can download it [here](https://metasfresh.com/en/download/#latest-server-update).

metasfresh can be installed via **Docker** or **Ubuntu Installer**.

**Docker** [How do I setup the metasfresh stack using Docker?](https://docs.metasfresh.org/installation_collection/EN/How_do_I_setup_the_metasfresh_stack_using_Docker)

**Ubuntu** [How do I install metasfresh using the Installation package?](https://docs.metasfresh.org/installation_collection/EN/installer_how_do_install_metasfresh_package.html)

>**First steps:**
>- [How do I log on?](https://docs.metasfresh.org/webui_collection/EN/Login.html)
>- [How do I change the Interface Language?](https://docs.metasfresh.org/webui_collection/EN/SwitchLanguage)
>- [How do I Setup my Company?](https://docs.metasfresh.org/webui_collection/EN/InitialSetupWizard)
>- [How do I create my first Sales Order?](https://docs.metasfresh.org/webui_collection/EN/SalesOrder_recording)
>- [How do I create my first Shipment?](https://docs.metasfresh.org/webui_collection/EN/Ship_SalesOrder)
>- [How do I create my first Invoice?](https://docs.metasfresh.org/webui_collection/EN/Invoice_SalesOrder)

## Documentationbadge
If you are new to metasfresh and would like to learn more, then you can find our documentation here:

- [Admins](https://docs.metasfresh.org/pages/installation/index_en)
- [Users](https://docs.metasfresh.org/pages/webui/index_en)
- [Developers](https://docs.metasfresh.org/index.html)
- [Tester](https://docs.metasfresh.org/pages/tests/index_en)

## Discussion
Come visit us in our [Community Forum](https://forum.metasfresh.org/) for questions, discussions and exchange of new insights. We look forward to meeting you!
<!-- GITTER LEGACY: Join one of the gitter rooms [metasfresh](https://gitter.im/metasfresh/metasfresh), [metasfresh-webui-frontend](https://gitter.im/metasfresh/metasfresh-webui-frontend), [metasfresh-documentation](https://gitter.im/metasfresh/metasfresh-documentation) or visit us in our [forum](https://forum.metasfresh.org/). -->

## Contributing
Do you want to help improving documentation, contribute some code or participate in functional requirements. That's great, you're welcome! Please read our [Code of Coduct](https://github.com/metasfresh/metasfresh/blob/master/CODE_OF_CONDUCT.md) and [Contibutor Guidelines](https://github.com/metasfresh/metasfresh/blob/master/CONTRIBUTING.md) first.

### "Monorepo"
To check out only certain parts of this repository, we recomment to get git version 2.25.0 or later and use the [git-sparse-checkout](https://www.git-scm.com/docs/git-sparse-checkout) feature.
Examples:
* to get started, do `git sparse-checkout init --clone`
  * this will leave you with just the files in the repo's root folder, such as the file you are reading
* to get the frontend code, do `git sparse-checkout set frontend`
* to go back to having everything checked out, do `git sparse-checkout disable`

## What's new in metasfresh ERP?
If you are interested in latest improvements or bug fixes of metasfresh ERP, feel free to check out our [Release Notes](https://github.com/metasfresh/metasfresh/blob/master/ReleaseNotes.md).
