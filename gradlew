#!/usr/bin/env sh
set -eu

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

if [ -x "$APP_HOME/gradlew" ]; then
    exec "$APP_HOME/gradlew" "$@"
fi

if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
fi

cat >&2 <<'EOF'
Gradle wrapper not found and no system gradle installation was detected.
Install Gradle or run:
  gradle wrapper
Then execute:
  ./gradlew clean build
EOF
exit 1
