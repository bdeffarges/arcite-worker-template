package com.idorsia.research.arcite.anyworkers

import com.idorsia.research.arcite.core.transforms.cluster.AddWorkerClusterClient
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

/**
  * arcite-catwalk
  *
  * Copyright (C) 2017 Idorsia Pharmaceuticals Ltd.
  * Gewerbestrasse 16
  * CH-4123 Allschwil, Switzerland.
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Created by Bernard Deffarges on 2017/05/09.
  *
  */
object Main extends App with LazyLogging {

  println(args.mkString(" ; "))

  println(s"config environment file: ${System.getProperty("config.resource")}")

  val config = ConfigFactory.load()

  val clusterClientAdd = new AddWorkerClusterClient("???-workers-actor-system", config)

//  clusterClientAdd.addWorkers(??Worker.definition, 2)
}
