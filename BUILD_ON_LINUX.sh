#!/usr/bin/env sh
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
SDK="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/Android/Sdk}}"
SDKMANAGER="${SDKMANAGER:-$SDK/cmdline-tools/latest/bin/sdkmanager}"
if [ ! -x "$SDKMANAGER" ]; then
  echo "sdkmanager not found. Install Android Command-line Tools and set ANDROID_SDK_ROOT." >&2
  exit 2
fi
export ANDROID_HOME="$SDK"
export ANDROID_SDK_ROOT="$SDK"
"$SDKMANAGER" --sdk_root="$SDK" --install "platform-tools" "platforms;android-37" "build-tools;36.0.0"
GRADLE_CMD=""
if [ -n "${GRADLE_HOME:-}" ] && [ -x "$GRADLE_HOME/bin/gradle" ]; then
  GRADLE_CMD="$GRADLE_HOME/bin/gradle"
elif [ -n "${BNKK_GRADLE_HOME:-}" ] && [ -x "$BNKK_GRADLE_HOME/bin/gradle" ]; then
  GRADLE_CMD="$BNKK_GRADLE_HOME/bin/gradle"
elif command -v gradle >/dev/null 2>&1; then
  GRADLE_CMD="$(command -v gradle)"
else
  GRADLE_CMD="$ROOT/gradlew"
fi
cd "$ROOT"
"$GRADLE_CMD" --no-daemon :app:assembleDebug
echo "APK: $ROOT/app/build/outputs/apk/debug/app-debug.apk"
