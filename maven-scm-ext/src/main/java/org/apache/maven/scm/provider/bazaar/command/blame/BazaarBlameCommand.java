/*
 * Copyright (C) 2010 Evgeny Mandrikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.maven.scm.provider.bazaar.command.blame;

import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.ScmResult;
import org.apache.maven.scm.command.Command;
import org.apache.maven.scm.command.blame.AbstractBlameCommand;
import org.apache.maven.scm.command.blame.BlameScmResult;
import org.apache.maven.scm.provider.ScmProviderRepository;
import org.apache.maven.scm.provider.bazaar.BazaarUtils;

/**
 * @author Evgeny Mandrikov
 */
public class BazaarBlameCommand extends AbstractBlameCommand implements Command {
  @Override
  public BlameScmResult executeBlameCommand(ScmProviderRepository repo, ScmFileSet workingDirectory, String filename) throws ScmException {
    String[] cmd = new String[]{"blame", "--all", "--long", filename};
    BazaarBlameConsumer consumer = new BazaarBlameConsumer(getLogger());
    ScmResult result = BazaarUtils.execute(consumer, getLogger(), workingDirectory.getBasedir(), cmd);
    if (!result.isSuccess()) {
      return new BlameScmResult(result.getCommandLine(), result.getProviderMessage(), result.getCommandOutput(), false);
    }
    return new BlameScmResult(result.getCommandLine(), consumer.getLines());
  }
}