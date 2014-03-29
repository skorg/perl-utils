package org.scriptkitty.perl.internal;

import org.scriptkitty.perl.compiler.CompilerErrorOrWarn;
import org.scriptkitty.perl.lang.Keyword;
import org.scriptkitty.perl.lang.Symbol;
import org.scriptkitty.perl.pod.PodErrorOrWarn;


class StaticContentManager implements IContentManager
{
    //~ Methods

    @Override public StaticContentProvider<?> getContentProvider(String baseName) throws InstantiationException
    {
        if (CompilerErrorOrWarn.compilerErrorsAndWarnings.equals(baseName))
        {
            return getCompilerEoWProvider(baseName);
        }

        if (PodErrorOrWarn.podErrorsAndWarnings.equals(baseName))
        {
            return getPodEoWProvider(baseName);
        }

        if (Symbol.symbols.equals(baseName))
        {
            return getSymbolProvider(baseName);
        }

        return getKeywordProvider(baseName);
    }

    private StaticContentProvider<CompilerErrorOrWarn> getCompilerEoWProvider(String baseName) throws InstantiationException
    {
        return new StaticContentProvider<CompilerErrorOrWarn>(baseName)
        {
            @Override protected Class<CompilerErrorOrWarn> getContentClass()
            {
                return CompilerErrorOrWarn.class;
            }

            @Override protected String getKey(CompilerErrorOrWarn errorOrWarning)
            {
                return errorOrWarning.toString();
            }
        };
    }

    private StaticContentProvider<Keyword> getKeywordProvider(String baseName) throws InstantiationException
    {
        return new StaticContentProvider<Keyword>(baseName)
        {
            @Override protected Class<Keyword> getContentClass()
            {
                return Keyword.class;
            }

            @Override protected String getKey(Keyword keyword)
            {
                return keyword.toString();
            }
        };
    }

    private StaticContentProvider<PodErrorOrWarn> getPodEoWProvider(String baseName) throws InstantiationException
    {
        return new StaticContentProvider<PodErrorOrWarn>(baseName)
        {
            @Override protected Class<PodErrorOrWarn> getContentClass()
            {
                return PodErrorOrWarn.class;
            }

            @Override protected String getKey(PodErrorOrWarn errorOrWarning)
            {
                return errorOrWarning.toString();
            }
        };
    }

    private StaticContentProvider<Symbol> getSymbolProvider(String baseName) throws InstantiationException
    {
        return new StaticContentProvider<Symbol>(baseName)
        {
            @Override protected Class<Symbol> getContentClass()
            {
                return Symbol.class;
            }

            @Override protected String getKey(Symbol symbol)
            {
                return symbol.toString();
            }
        };
    }
}
