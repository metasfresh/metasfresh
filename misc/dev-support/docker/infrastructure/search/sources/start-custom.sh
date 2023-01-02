#!/bin/bash

#
# %L
# metasfresh
# %%
# Copyright (C) 2023 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

# Give the folder to the docker image's elasticsearch user.
# This is needed so that we can mount it on the dockerhost.
# Also see https://discuss.elastic.co/t/elastic-elasticsearch-docker-not-assigning-permissions-to-data-directory-on-run/65812/2
/usr/bin/chown 1000:0 /usr/share/elasticsearch/data

# now call the actual entry point script that is in the original Dockerfile's ENTRYPOINT
/usr/local/bin/docker-entrypoint.sh
