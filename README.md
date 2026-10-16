# Forge 1.20.1 Mod Compiler

A small HTML frontend plus GitHub Actions workflow that builds a Forge 1.20.1 Java mod on a GitHub-hosted Ubuntu runner. Useful when developing from a phone: compilation does not run locally.

## Setup
1. Keep this repository public if you want to host the page with GitHub Pages.
2. Go to **Settings → Pages** and set the source to **Deploy from a branch**, branch **main**, folder **/(root)**. Save.
3. Open the Pages URL (usually `https://OWNER.github.io/REPOSITORY/`).
4. Create a fine-grained personal access token at https://github.com/settings/personal-access-tokens/new. Restrict it to this repository and grant **Actions: Read and write**. Do not commit the token or share it.
5. On the page, enter the token, mod details, and Java source, then press **Build .jar on GitHub**.
6. When successful, open the Actions run and download the **compiled-forge-mod** artifact ZIP. The compiled JAR is inside it.

## Java source contract
This starter currently compiles one source file at `src/main/java/com/example/examplemod/ExampleMod.java`. Keep this package and class, and use the annotation placeholder so the compiler can set your chosen mod ID:

```java
package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;

@Mod("__MOD_ID__")
public class ExampleMod {
    public ExampleMod() {
        System.out.println("My Forge mod loaded!");
    }
}
```

Use Java 17 and Forge 1.20.1 (47.4.10). More complex mods can need additional source files, assets, dependencies, and Gradle changes; this basic starter only injects the single class.

## Security
The token is typed into the page and held in JavaScript memory only; it is not saved in local storage or sent anywhere except GitHub API requests. Do not enter it into source code. Anyone who has the token may be able to act within the permissions you grant.

## Notes
- GitHub Actions uses hosted runners and public repository Actions minutes are subject to GitHub's current policies.
- Artifacts are retained for 7 days.
