- scala 打包
```
mvn clean scala:compile  compile
```
- 
```markdown
主构造函数列表中的参数:
如果只有var/val修饰，则字段总是对外可见的，即默认是public的
如果连var/val也没有，则字段对外不可见，同时内部只读，不可改写，即默认是：private val
第二条不适用于case class，case class的类参数在不指定val/var修饰时，会自动编译为public val，即对外只读，如果需要case class的字段外部可写，可以显式地指定var关键字！
```
- 
```markdown
如果字段被声明为 var, Scala 会为该字段生成 getter 方法和 setter 方法, 方法的可见性是 public
如果字段被声明为 val, Scala 会只为该字段生成 getter 方法, 方法的可见性是 public
如果字段被声明为 private var, Scala 会为该字段生成 getter方法和 setter方法, 方法的可见性是 private
如果字段被声明为 private val, Scala 会只为该字段生成 getter 方法, 方法的可见性是 private
```

