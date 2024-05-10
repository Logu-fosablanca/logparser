@echo off
for %%f in (com\logparser\aggregators\*.java) do (
    javac %%f
)
for %%f in (com\logparser\logs\*.java) do (
    javac %%f
)
for %%f in (com\logparser\parser\*.java) do (
    javac %%f
)
for %%f in (com\logparser\visitors\*.java) do (
    javac %%f
)
for %%f in (com\logparser\*.java) do (
    javac %%f
)