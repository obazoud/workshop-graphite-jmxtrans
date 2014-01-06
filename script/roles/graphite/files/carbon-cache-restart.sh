#!/bin/bash

echo "Restarting carbon-cache.py"

/opt/graphite/bin/carbon-cache.py stop
sleep 5
/opt/graphite/bin/carbon-cache.py start
