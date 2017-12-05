/*
 *     Copyright 2015-2016 Austin Keener & Michael Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package net.dv8tion.jda.entities.impl;

import java.util.Objects;
import net.dv8tion.jda.entities.Game;

public class GameImpl implements Game
{
    private final String name;
    private final String url;
    private final GameType type;

    public GameImpl(String name, String url, GameType type)
    {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getUrl()
    {
        return url;
    }

    @Override
    public GameType getType()
    {
        return type;
    }


    @Override
    public boolean equals(Object obj)
    {
        if(!( obj instanceof Game ))
            return false;
        Game other = (Game) obj;
        return ( other.getType() == type ) && 
               ( (name == null && other.getName() == null) || (name != null && name.equals(other.getName())) ) && 
               ( (url == null && other.getUrl() == null) || (url != null && url.equals(other.getUrl())) );
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.url);
        hash = 31 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
