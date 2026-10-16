# Forge 1.20.1 Mod Compiler

A browser frontend and GitHub Actions workflows that compile Forge 1.20.1 mods on a GitHub-hosted Ubuntu runner. It works from a phone because compilation runs on GitHub.

## Set up GitHub Pages

1. Keep this repository public to use GitHub Pages easily.
2. Open **Settings → Pages**: https://github.com/Memegod12344321/Forge-1.20.1-Compiler/settings/pages
3. Choose **Deploy from a branch**, branch `main`, folder `/(root)`, and save.
4. Open https://memegod12344321.github.io/Forge-1.20.1-Compiler/ after deployment finishes.

## Create the token

Create a fine-grained personal access token at https://github.com/settings/personal-access-tokens/new.

- Resource owner: your own GitHub account.
- Repository access: **Only select repositories** → `Forge-1.20.1-Compiler`.
- Repository permissions: **Actions — Read and write**.
- Repository permissions: **Contents — Read and write** (required to upload a ZIP to this public repository).

Do not commit or share the token. The page keeps it in memory only, not local storage. Anyone holding the token can act within its granted permissions.

## Build from a ZIP

1. On the compiler page, enter the token and choose **Upload full Forge project ZIP**.
2. Select a ZIP containing a complete Gradle project: `gradlew`, `build.gradle` or `build.gradle.kts`, and `src/`.
3. The page reads `src/main/resources/META-INF/mods.toml` first, then `gradle.properties`, to auto-fill the display name and mod ID when those values are present.
4. Tap **Build .jar on GitHub**. The page uploads the ZIP as a base64 text file under `uploads/` and starts `.github/workflows/build-zip.yml`.
5. When the workflow succeeds, open the Actions run and download the `compiled-forge-mod` artifact ZIP. The built JAR is inside it.

**Public repository warning:** uploaded project ZIPs are committed under `uploads/` as base64 text. The ZIP contents are public and may remain in Git history even if the file is deleted later. Do not upload private source, credentials, signing keys, or other secrets. The browser uploader currently limits ZIP files to 8 MB.

The ZIP workflow runs the uploaded project's Gradle build script. Only upload projects you trust. It does not expose any repository secrets, and the workflow token has read-only contents permission.

## Build a starter mod from pasted Java

Choose **Paste Java starter**, keep the required package/class and `@Mod("__MOD_ID__")` placeholder, set the name and mod ID, and start the build. This mode uses `.github/workflows/build-mod.yml` and Forge 1.20.1 (47.4.10) with Java 17.

## Artifacts and troubleshooting

- Artifacts are retained for 7 days.
- Check [GitHub Actions](https://github.com/Memegod12344321/Forge-1.20.1-Compiler/actions) for logs if a build fails.
- If ZIP upload returns HTTP 403, check that the token has **Contents: Read and write** and **Actions: Read and write** for this repository.
- A ZIP without a Gradle wrapper or build file is not a complete buildable project.
